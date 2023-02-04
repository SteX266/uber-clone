import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AuthService } from 'src/app/services/auth/auth.service';
import { BalanceComponent } from '../balance/balance.component';

@Component({
  selector: 'app-clinet-navbar',
  templateUrl: './clinet-navbar.component.html',
  styleUrls: ['./clinet-navbar.component.scss'],
})
export class ClinetNavbarComponent {
  userId = '';

  constructor(private authService: AuthService, public dialog:MatDialog) {}

  ngOnInit() {
    this.userId = this.authService.getCurrentUserId();
  }

  logOut() {
    this.authService.logout();
  }

  openBalanceDialog(
    enterAnimationDuration: string,
    exitAnimationDuration: string
  ): void {
    this.dialog.open(BalanceComponent, {
      width: '300px',
      enterAnimationDuration,
      exitAnimationDuration,
    });
  }
}
