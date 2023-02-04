import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { RideCardComponent } from './components/ride-card/ride-card.component';
import { BrowserModule } from '@angular/platform-browser';
import { FooterComponent } from './components/footer/footer.component';
import { ChartModule } from 'primeng/chart';
import { FormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ChatComponent } from './pages/chat/chat.component';
import { RidesHistoryPageComponent } from './pages/rides-history-page/rides-history-page.component';
import { ReportPageComponent } from './pages/report-page/report-page.component';
import { CalendarModule } from 'primeng/calendar';
import { ChartComponent } from './components/chart/chart.component';

@NgModule({
  declarations: [
    RideCardComponent,
    FooterComponent,
    ChatComponent,
    RidesHistoryPageComponent,
    ReportPageComponent,
    ChartComponent,
  ],
  imports: [
    RouterModule,
    BrowserModule,
    FormsModule,
    NgbModule,
    ChartModule,
    CalendarModule,
  ],
  exports: [
    RideCardComponent,
    FooterComponent,
    ChatComponent,
    RidesHistoryPageComponent,
    ReportPageComponent,
  ],
})
export class SharedModule {}
