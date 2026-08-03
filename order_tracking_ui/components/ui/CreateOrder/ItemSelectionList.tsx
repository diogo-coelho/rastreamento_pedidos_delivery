"use client";

import { ItemResponseData } from "@/types/item";
import { Checkbox, InputNumber } from "antd";

interface ItemSelectionListProps {
  items: ItemResponseData[];
  selectedItems: Record<string, number>;
  onToggleItem: (itemId: string, checked: boolean) => void;
  onChangeQuantity: (itemId: string, quantity: number) => void;
}

const formatCurrency = (value: number) => {
  return new Intl.NumberFormat("pt-BR", {
    style: "currency",
    currency: "BRL",
  }).format(value);
};

export default function ItemSelectionList({
  items,
  selectedItems,
  onToggleItem,
  onChangeQuantity,
}: ItemSelectionListProps) {
  if (items.length === 0) {
    return <p>Nenhum item disponivel para pedido.</p>;
  }

  return (
    <div className="mt-4 flex flex-col gap-3">
      {items.map((item, index) => {
        const itemId = item.id ?? "";
        const isSelectable = Boolean(itemId);
        const isChecked = itemId ? itemId in selectedItems : false;
        const quantity = itemId && selectedItems[itemId] ? selectedItems[itemId] : 1;

        return (
          <div
            key={`${item.name}-${index}`}
            className="rounded-md border border-neutral-200 p-3"
          >
            <div className="flex flex-col gap-3 md:flex-row md:items-center md:justify-between">
              <div className="flex items-start gap-3">
                <Checkbox
                  checked={isChecked}
                  disabled={!isSelectable}
                  onChange={(event) => {
                    if (!itemId) {
                      return;
                    }
                    onToggleItem(itemId, event.target.checked);
                  }}
                >
                  <span className="font-semibold text-neutral-900">{item.name}</span>
                </Checkbox>

                {!isSelectable && (
                  <span className="text-sm text-amber-700">
                    Item sem id nao pode ser selecionado
                  </span>
                )}
              </div>

              <div className="flex items-center gap-3">
                <span className="text-sm text-neutral-600">
                  {formatCurrency(item.price)}
                </span>
                <InputNumber
                  min={1}
                  value={quantity}
                  disabled={!isChecked}
                  onChange={(value) => {
                    if (!itemId) {
                      return;
                    }
                    onChangeQuantity(itemId, Number(value) || 1);
                  }}
                />
              </div>
            </div>

            <p className="mt-2 text-sm text-neutral-500">{item.description}</p>
          </div>
        );
      })}
    </div>
  );
}