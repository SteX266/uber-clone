import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserProfileInfo } from 'src/app/models/user-profile-info';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss'],
})
export class UserProfileComponent {
  selectedId = 0;
  user!: UserProfileInfo;
  constructor(
    private route: ActivatedRoute,
    private userService: UserService
  ) {}

  ngOnInit() {
    this.route.params.subscribe((params) => {
      this.selectedId = Number(params['id']);
      this.user = this.userService.getUserById(this.selectedId);
    });
  }
}
