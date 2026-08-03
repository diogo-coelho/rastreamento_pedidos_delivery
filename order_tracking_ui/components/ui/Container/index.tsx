"use client";

import { ContainerProps } from "./container";

const Container = (props: ContainerProps) => {

  return (
    <div className="max-w-[2520px] mx-auto xl:px-20 md:px-10 sm:px-2 px-4">
      <div>
        {props.children}
      </div>
    </div>
  );
};

export default Container;