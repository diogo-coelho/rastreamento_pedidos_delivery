"use client";

import Card from '@/components/ui/Card';
import DeliveryAddressAccordion from '@/components/ui/CreateOrder/DeliveryAddressAccordion';
import ItemSelectionList from '@/components/ui/CreateOrder/ItemSelectionList';
import OrderRequestSummary from '@/components/ui/CreateOrder/OrderRequestSummary';
import { listAddresses } from '@/services/address.service';
import { ListItems } from '@/services/item.service';
import { createOrder } from '@/services/order.service';
import { AddressResponseData } from '@/types/address';
import { ItemResponseData } from '@/types/item';
import { OrderRequestData } from '@/types/order';
import { Alert, Button, Spin, message } from 'antd';
import { useEffect, useMemo, useState } from 'react';

const decodeJwtPayload = (token: string): Record<string, unknown> | null => {
  try {
    const payload = token.split('.')[1];
    if (!payload) {
      return null;
    }

    const normalizedPayload = payload.replace(/-/g, '+').replace(/_/g, '/');
    const paddedPayload = normalizedPayload.padEnd(
      Math.ceil(normalizedPayload.length / 4) * 4,
      '='
    );

    const decodedPayload = atob(paddedPayload);
    return JSON.parse(decodedPayload) as Record<string, unknown>;
  } catch {
    return null;
  }
};

const getUserIdFromToken = (token: string | null): string | null => {
  if (!token) {
    return null;
  }

  const payload = decodeJwtPayload(token);
  if (!payload) {
    return null;
  }

  const userId = payload.userId;
  console.log("Decoded userId from token:", userId);
  if (typeof userId !== 'string') {
    return null;
  }

  if (userId.includes('@')) {
    return null;
  }

  return userId;
};

export default function CreateOrderPage() {
  const [items, setItems] = useState<ItemResponseData[]>([]);
  const [addresses, setAddresses] = useState<AddressResponseData[]>([]);
  const [selectedItems, setSelectedItems] = useState<Record<string, number>>({});
  const [selectedAddressId, setSelectedAddressId] = useState<string | null>(null);
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const loadData = async () => {
      try {
        setLoading(true);
        setError(null);

        const [itemsResponse, addressesResponse] = await Promise.all([
          ListItems(),
          listAddresses(),
        ]);

        setItems(itemsResponse);
        setAddresses(addressesResponse);
      } catch (loadError) {
        console.error(loadError);
        setError('Nao foi possivel carregar itens e enderecos.');
      } finally {
        setLoading(false);
      }
    };

    loadData();
  }, []);

  const selectedItemCount = Object.keys(selectedItems).length;

  const orderPayload = useMemo((): OrderRequestData | null => {
    const token = localStorage.getItem('token');
    const userId = getUserIdFromToken(token);

    if (!userId || !selectedAddressId || selectedItemCount === 0) {
      return null;
    }

    const payloadItems = Object.entries(selectedItems).map(([itemId, quantity]) => ({
      itemId,
      quantity,
    }));

    return {
      userId,
      addressId: selectedAddressId,
      items: payloadItems,
    };
  }, [selectedAddressId, selectedItemCount, selectedItems]);

  const selectedTotal = useMemo(() => {
    const selectedById = new Map(Object.entries(selectedItems));
    return items.reduce((acc, item) => {
      if (!item.id || !selectedById.has(item.id)) {
        return acc;
      }

      return acc + item.price * Number(selectedById.get(item.id));
    }, 0);
  }, [items, selectedItems]);

  const handleToggleItem = (itemId: string, checked: boolean) => {
    setSelectedItems((currentState) => {
      if (checked) {
        return {
          ...currentState,
          [itemId]: currentState[itemId] ?? 1,
        };
      }

      const updatedState = { ...currentState };
      delete updatedState[itemId];
      return updatedState;
    });
  };

  const handleChangeQuantity = (itemId: string, quantity: number) => {
    setSelectedItems((currentState) => ({
      ...currentState,
      [itemId]: Math.max(1, quantity),
    }));
  };

  const handleSubmitOrder = async () => {
    if (!orderPayload) {
      message.error('Selecione itens e endereco para criar o pedido.');
      return;
    }

    const token = localStorage.getItem('token');
    const userId = getUserIdFromToken(token);
    if (!userId) {
      message.error('Nao foi possivel obter o id do usuario a partir do token.');
      return;
    }

    try {
      setSubmitting(true);
      await createOrder(orderPayload);
      message.success('Pedido criado com sucesso.');
      window.location.href = '/';
    } catch (submitError) {
      console.error(submitError);
      message.error('Erro ao criar pedido.');
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <Card>
      <div className="flex justify-between h-full">
        <h1 className="text-1xl font-bold">Criação de Pedido</h1>
      </div>

      {loading && (
        <div className="mt-6">
          <Spin />
        </div>
      )}

      {!loading && error && (
        <div className="mt-6">
          <Alert type="error" message={error} />
        </div>
      )}

      {!loading && !error && (
        <>
          <div className="flex flex-col gap-8">
          <ItemSelectionList
            items={items}
            selectedItems={selectedItems}
            onToggleItem={handleToggleItem}
            onChangeQuantity={handleChangeQuantity}
          />

          {selectedItemCount > 0 && (
            <DeliveryAddressAccordion
              addresses={addresses}
              selectedAddressId={selectedAddressId}
              onSelectAddress={setSelectedAddressId}
            />
          )}
          </div>

          <OrderRequestSummary payload={orderPayload} total={selectedTotal} />

          <div className="mt-6 flex justify-end">
            <Button
              type="primary"
              onClick={handleSubmitOrder}
              loading={submitting}
              disabled={!orderPayload}
            >
              Criar pedido
            </Button>
          </div>
        </>
      )}

    </Card>
  );
}