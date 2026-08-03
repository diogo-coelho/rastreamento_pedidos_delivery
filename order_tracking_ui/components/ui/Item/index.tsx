import { ItemProps } from "./item";

const DEFAULT_ITEM_IMAGE = "https://placehold.co/80x80?text=Item";

const Item = (props: ItemProps) => {
  const itemSubtotal = props.item.price * props.item.quantity;
  const itemTotalFormatted = new Intl.NumberFormat("pt-BR", {
    style: "currency",
    currency: "BRL",
  }).format(itemSubtotal);

  const itemUnitPriceFormatted = new Intl.NumberFormat("pt-BR", {
    style: "currency",
    currency: "BRL",
  }).format(props.item.price);

  const imageUrl = props.item.imageUrl?.trim() || DEFAULT_ITEM_IMAGE;

  return (
    <li className="flex items-center justify-between gap-3 border-b border-neutral-200 py-3 last:border-b-0">
      <div className="flex items-center gap-3">
        <img
          src={imageUrl}
          alt={props.item.name}
          className="h-14 w-14 rounded object-cover"
          onError={(event) => {
            if (event.currentTarget.src !== DEFAULT_ITEM_IMAGE) {
              event.currentTarget.src = DEFAULT_ITEM_IMAGE;
            }
          }}
        />
        <div>
          <p className="font-semibold text-neutral-900">{props.item.name}</p>
          <p className="text-sm text-neutral-500">
            {props.item.quantity} x {itemUnitPriceFormatted}
          </p>
        </div>
      </div>

      <p className="font-semibold text-neutral-900">{itemTotalFormatted}</p>
    </li>
  );
};

export default Item;