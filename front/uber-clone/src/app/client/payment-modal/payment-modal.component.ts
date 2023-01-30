import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { PaymentDTO } from 'src/app/models/payment-dto.model';
import { PaymentService } from 'src/app/services/payment/payment.service';

@Component({
  selector: 'app-payment-modal',
  templateUrl: './payment-modal.component.html',
  styleUrls: ['./payment-modal.component.scss'],
})
export class PaymentModalComponent {
  constructor(
    public dialogRef: MatDialogRef<PaymentModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: PaymentDTO,
    private paymentService: PaymentService
  ) {}
  confirmPayment() {
    this.paymentService.confirmPayment(this.data);
  }
}
