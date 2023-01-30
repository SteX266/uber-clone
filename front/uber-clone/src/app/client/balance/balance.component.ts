import { Component } from '@angular/core';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-balance',
  templateUrl: './balance.component.html',
  styleUrls: ['./balance.component.scss']
})
export class BalanceComponent {
  coinAmount:number=0;
  constructor(private userService:UserService){

    this.coinAmount = this.userService.getCurrentUserCoinAmount();
  }

}
