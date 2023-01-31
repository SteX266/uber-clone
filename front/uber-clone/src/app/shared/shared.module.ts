import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { RideCardComponent } from './ride-card/ride-card.component';
import { BrowserModule } from '@angular/platform-browser';
import { FooterComponent } from './footer/footer.component';

import { FormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ChatComponent } from './chat/chat.component';
import { RidesHistoryPageComponent } from './rides-history-page/rides-history-page.component';

@NgModule({
  declarations: [
    RideCardComponent,
    FooterComponent,
    ChatComponent,
    RidesHistoryPageComponent,
  ],
  imports: [RouterModule, BrowserModule, FormsModule, NgbModule],
  exports: [
    RideCardComponent,
    FooterComponent,
    ChatComponent,
    RidesHistoryPageComponent,
  ],
})
export class SharedModule {}
