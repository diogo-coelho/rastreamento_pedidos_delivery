"use client";

import Card from '@/components/ui/Card';
import Order from '@/components/ui/Order';
import { listOrders } from '@/services/order.service';
import { OrderResponseData } from '@/types/order';
import { Button } from 'antd';
import { useEffect, useState } from 'react';

export default function HomePage() {
  const [orders, setOrders] = useState<OrderResponseData[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const loadOrders = async () => {
      try {
        setLoading(true);
        setError(null);
        const response = await listOrders();
        setOrders(response);
      } catch (loadError) {
        console.error(loadError);
        setError('Nao foi possivel carregar os pedidos.');
      } finally {
        setLoading(false);
      }
    };

    loadOrders();
  }, []);


  return (
    <Card>
      <div className="flex justify-between h-full">
        <h1 className="text-1xl font-bold">Listagem de Pedidos</h1>
        <Button 
          type="primary" 
          className="w-24 small:w-auto" 
          onClick={() => window.location.href = '/create-order'}>
          Criar Pedido
        </Button>
      </div>

      <div className="mt-6 flex flex-col gap-4">
        {loading && <p>Carregando pedidos...</p>}

        {!loading && error && (
          <p className="text-red-600">{error}</p>
        )}

        {!loading && !error && orders.length === 0 && (
          <p>Nenhum pedido encontrado.</p>
        )}

        {!loading && !error && orders.map((order) => (
          <Order key={order.id} order={order} />
        ))}
      </div>
    </Card>
  );
}
