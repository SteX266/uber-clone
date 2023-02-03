import { Component } from '@angular/core';
import { UserProfileInfo } from 'src/app/models/user-profile-info';
import { UpdateRequestService } from 'src/app/services/update-requests/update-request.service';
import { FormControl, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-update-profile-requests',
  templateUrl: './update-profile-requests.component.html',
  styleUrls: ['./update-profile-requests.component.scss'],
})
export class UpdateProfileRequestsComponent {
  requests: UserProfileInfo[] = [];

  constructor(private updateRequestService: UpdateRequestService) {}

  ngOnInit() {
    this.updateRequestService.getAllUpdateUserRequest().subscribe((data) => {
      this.requests = data;
    });
  }
}
