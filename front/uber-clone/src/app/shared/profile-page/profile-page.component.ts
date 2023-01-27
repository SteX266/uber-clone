import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
})
export class ProfilePageComponent {
  userType = '';
  constructor(
    private userService: UserService,
    private authService: AuthService,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.route.params.subscribe((params) => {
      let id = params['id'];
      this.userType += this.authService.getCurrentUserRole();
      if (id === this.authService.getCurrentUserId()) {
        this.userType += '-current';
      } else {
        this.userType += '-other-';
        this.userType += this.userService.getUserById(Number(id)).role;
      }
    });
  }
}
