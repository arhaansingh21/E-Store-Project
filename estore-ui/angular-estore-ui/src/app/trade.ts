import { Product } from "./product"

export interface Trade {
    product : Product
    status : string
  }

export enum Status {
  ACCEPTED = "ACCEPTED",
  DENIED = "DENIED",
  PENDING = "PENDING",
  CANCELED = "CANCELED"
}