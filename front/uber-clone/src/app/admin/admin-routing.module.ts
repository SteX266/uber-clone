import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ClientHomePageComponent } from '../client/pages/client-home-page/client-home-page.component';
import { ChangePasswordComponent } from '../profile/pages/change-password/change-password.component';
import { ChangePhotoComponent } from '../profile/pages/change-photo/change-photo.component';
import { ProfilePageComponent } from '../profile/pages/profile-page/profile-page.component';
import { UserPersonalInfoUpdateComponent } from '../profile/pages/user-personal-info-update/user-personal-info-update.component';
import { ChatComponent } from '../shared/pages/chat/chat.component';
import { RidesHistoryPageComponent } from '../shared/pages/rides-history-page/rides-history-page.component';
import { AdminNavbarComponent } from './components/admin-navbar/admin-navbar.component';
import { NotesComponent } from './pages/notes/notes.component';
import { UpdateProfileRequestsComponent } from './pages/update-profile-requests/update-profile-requests.component';
import { UpdateRequestComponent } from './pages/update-request/update-request.component';

const routes: Routes = [
  {
    path: 'admin',
    component: AdminNavbarComponent,
    children: [
      { path: 'profile/:id', component: ProfilePageComponent },
      {
        path: 'edit-personal-info',
        component: UserPersonalInfoUpdateComponent,
      },
      { path: 'edit-password', component: ChangePasswordComponent },
      { path: 'edit-picture', component: ChangePhotoComponent },
      { path: 'support', component: ChatComponent },
      {
        path: 'profile-change-requests',
        component: UpdateProfileRequestsComponent,
      },
      {
        path: 'profile-change-request/:id',
        component: UpdateRequestComponent,
      },
      { path: 'history', component: RidesHistoryPageComponent },
      {
        path: 'notes/:id',
        component: NotesComponent,
      },
      { path: '', component: ClientHomePageComponent },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AdminRoutingModule {}
