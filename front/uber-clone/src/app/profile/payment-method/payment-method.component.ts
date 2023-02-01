import { Component } from '@angular/core';
import { Location } from '@angular/common';
import { loadScript } from '@paypal/paypal-js';
import { SnackBarService } from 'src/app/services/snackbar/snackbar.service';
import { UserService } from 'src/app/services/user/user.service';


@Component({
  selector: 'app-payment-method',
  templateUrl: './payment-method.component.html',
  styleUrls: ['./payment-method.component.scss']
})
export class PaymentMethodComponent {
  
  coinAmount:number = 0;
  constructor(private location:Location,private snackBarService:SnackBarService, private userService:UserService){}
  CLIENT_ID = 'AdRDOhvqk-LrQliSkjBFysGnalYAgGK2AOcBhnx71Zo7Nh48GIGLUWLAa3S-E4h0QiAp7dX5nhXzry3I';


  ngOnInit(){
    
    loadScript({ 'client-id': this.CLIENT_ID }).then((paypal) => {
      if(paypal){
        paypal.Buttons?.({
          createOrder: this.createOrder,
          onApprove: this.onApprove,
        })
        .render('#paypal-button-container').catch((err)=>{

        });
      }

    }).catch((err)=>{

    });
  }

  buyTokens(){
    console.log(this.coinAmount);
  }

  back(): void {
    this.location.back();
  }


  createOrder = (data: any, actions: any) => {
    return actions.order.create({
      purchase_units: [
        {
          amount: {
            value: this.coinAmount/100,
          },
        },
      ],
    });
  }

  onApprove = (data: any, actions: any) => {
    this.snackBarService.openSuccessSnackBar("Order approved!");
    return actions.order.capture().then(
      this.snackBarService.openSuccessSnackBar("Order successful!"),
      this.userService.addCoins(this.coinAmount).subscribe()
  
  
    );
  }

}
