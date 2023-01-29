import { AfterViewChecked, Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserProfileInfo } from 'src/app/models/user-profile-info';
import { AuthService } from 'src/app/services/auth/auth.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
})
export class ProfilePageComponent {
  userType = '';
  id = '';
  user!: UserProfileInfo;
  constructor(
    private userService: UserService,
    private authService: AuthService,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.route.params.subscribe((params) => {
      this.id = params['id'];
      this.user = this.userService.getUserById(Number(this.id));
    });

    this.getUserType();
  }

  getUserType(): void {
    this.userType += this.authService.getCurrentUserRole();
    if (this.id === this.authService.getCurrentUserId()) {
      this.userType += '-current';
    } else {
      this.userType += '-other';
    }
    console.log(this.userType);
  }
}
