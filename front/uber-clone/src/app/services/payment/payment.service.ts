import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PaymentDTO } from 'src/app/models/payment-dto.model';
import { environment } from 'src/environments/environment';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root',
})
export class PaymentService {
  constructor(private http: HttpClient, private authService: AuthService) {}

  confirmPayment(paymentDTO: PaymentDTO) {
    let url = environment.apiEndpoint + 'reservation/confirmPayment';
    return this.http.post(
      url,
      paymentDTO,
      this.authService.getHttpOptionsWithToken()
    );
  }
  cancelPayment(paymentDTO: PaymentDTO) {
    let url = environment.apiEndpoint + 'reservation/cancelPayment';
    return this.http.post(
      url,
      paymentDTO,
      this.authService.getHttpOptionsWithToken()
    );
  }
}
