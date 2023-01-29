import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { RideCardComponent } from './ride-card/ride-card.component';
import { BrowserModule } from '@angular/platform-browser';
import { FooterComponent } from './footer/footer.component';

import { FormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ChatComponent } from './chat/chat.component';

@NgModule({
  declarations: [RideCardComponent, FooterComponent, ChatComponent],
  imports: [RouterModule, BrowserModule, FormsModule, NgbModule],
  exports: [RideCardComponent, FooterComponent, ChatComponent],
})
export class SharedModule {}
