import { Component } from '@angular/core';
import { UserService } from 'src/app/services/user/user.service';
import { MatDialogRef } from '@angular/material/dialog';


@Component({
  selector: 'app-balance',
  templateUrl: './balance.component.html',
  styleUrls: ['./balance.component.scss']
})
export class BalanceComponent {

  coinAmount:number=0;
  constructor(private userService:UserService,public dialogRef: MatDialogRef<BalanceComponent>){
    
  }
  ngOnInit(){
    this.userService.sendGetCurrentUserCoinAmountRequest().subscribe({next:(val)=>{
      this.coinAmount = val;
    }});


  }
  closeModal(){
    this.dialogRef.close();
  }

}
