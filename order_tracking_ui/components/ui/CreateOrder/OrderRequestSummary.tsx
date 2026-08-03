"use client";

import { OrderRequestData } from "@/types/order";

interface OrderRequestSummaryProps {
  payload: OrderRequestData | null;
  total: number;
}

const formatCurrency = (value: number) => {
  return new Intl.NumberFormat("pt-BR", {
    style: "currency",
    currency: "BRL",
  }).format(value);
};

export default function OrderRequestSummary({ payload, total }: OrderRequestSummaryProps) {
  if (!payload) {
    return null;
  }

  return (
    <section className="mt-6 rounded-md border border-neutral-200 p-4">
      <h2 className="text-base font-semibold">Resumo do pedido</h2>
      <p className="mt-2 text-sm text-neutral-700 font-bold">Total: {formatCurrency(total)}</p>
    </section>
  );
}