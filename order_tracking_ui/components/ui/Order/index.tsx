import { OrderProps } from "./order";
import Item from "@/components/ui/Item";

const statusLabels: Record<string, string> = {
  RECEBIDO: "Recebido",
  EM_PREPARO: "Em preparo",
  SAIU_PARA_ENTREGA: "Saiu para entrega",
  ENTREGUE: "Entregue",
  CANCELADO: "Cancelado",
};

const statusByIndex = [
  "Recebido",
  "Em preparo",
  "Saiu para entrega",
  "Entregue",
  "Cancelado",
];

const resolveStatusLabel = (status: string | number) => {
  if (typeof status === "number") {
    return statusByIndex[status] ?? "Desconhecido";
  }

  return statusLabels[status] ?? status;
};

const formatDate = (date: string) => {
  console.log("formatDate", date);
  const parsedDate = new Date(date);

  if (Number.isNaN(parsedDate.getTime())) {
    return "Data invalida";
  }

  return new Intl.DateTimeFormat("pt-BR", {
    dateStyle: "short",
    timeStyle: "short",
  }).format(parsedDate);
};

const Order = (props: OrderProps) => {
  const { order } = props;
  const address = `${order.address.street}, ${order.address.number} - ${order.address.city}/${order.address.state}, ${order.address.country} - CEP ${order.address.postalCode}`;
  const total = order.items.reduce((acc, item) => acc + item.price * item.quantity, 0);
  const totalFormatted = new Intl.NumberFormat("pt-BR", {
    style: "currency",
    currency: "BRL",
  }).format(total);

  return (
    <article className="rounded-lg border border-neutral-200 bg-white p-4 shadow-sm">
      <div className="mb-3 flex flex-col gap-2 md:flex-row md:items-start md:justify-between">
        <div>
          <h2 className="text-lg font-bold text-neutral-900">Pedido #{order.id}</h2>
          <p className="text-sm text-neutral-600">
            Criado em {formatDate(order.createdAt)}
          </p>
        </div>

        <div className="rounded-full bg-neutral-100 px-3 py-1 text-sm font-medium text-neutral-700">
          {resolveStatusLabel(order.status as string | number)}
        </div>
      </div>

      <div className="mb-4 grid gap-2 text-sm text-neutral-700">
        <p>
          <strong>Cliente:</strong> {order.user.name}
        </p>
        <p>
          <strong>E-mail:</strong> {order.user.email}
        </p>
        <p>
          <strong>Endereco:</strong> {address}
        </p>
      </div>

      <ul className="mb-4">
        {order.items.map((item, index) => (
          <Item key={`${order.id}-${item.name}-${index}`} item={item} />
        ))}
      </ul>

      <div className="flex items-center justify-end border-t border-neutral-200 pt-3">
        <p className="text-base font-bold text-neutral-900">Total: {totalFormatted}</p>
      </div>
    </article>
  );
};

export default Order;