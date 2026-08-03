"use client";

import { CardProps } from "./card";
import './Card.scss';

const Card = (props: CardProps) => {

  return (
    <div className="card">
        {props.children}
    </div>
  );
};

export default Card;