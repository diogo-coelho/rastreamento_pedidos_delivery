"use client";

import { ContainerProps } from "./container";
import "./Container.scss";

const Container = (props: ContainerProps) => {

  return (
    <div className="container">
      <div className="main-container">
        {props.children}
      </div>
    </div>
  );
};

export default Container;