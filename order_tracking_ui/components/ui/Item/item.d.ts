export interface OrderItemData {
	name: string;
	imageUrl: string;
	price: number;
	quantity: number;
}

export interface ItemProps {
	item: OrderItemData;
}