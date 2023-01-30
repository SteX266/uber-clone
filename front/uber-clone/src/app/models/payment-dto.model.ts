export class PaymentDTO {
  customerEmail: string;
  reservationId: number;
  amount: number;

  constructor(customerEmail: string, reservationId: number, amount: number) {
    this.customerEmail = customerEmail;
    this.reservationId = reservationId;
    this.amount = amount;
  }
}
