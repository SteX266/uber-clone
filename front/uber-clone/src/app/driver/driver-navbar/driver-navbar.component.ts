import { Component } from '@angular/core';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-driver-navbar',
  templateUrl: './driver-navbar.component.html',
  styleUrls: ['./driver-navbar.component.scss']
})
export class DriverNavbarComponent {


  constructor(private authService:AuthService){}
  logOut(){
    this.authService.logout();
  }

}
