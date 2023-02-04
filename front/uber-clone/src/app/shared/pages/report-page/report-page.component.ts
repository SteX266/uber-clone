import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Report } from 'src/app/models/report';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-report-page',
  templateUrl: './report-page.component.html',
  styleUrls: ['./report-page.component.scss'],
})
export class ReportPageComponent {
  basicData: any;
  horizontalOptions: any;
  id = 0;
  rangeDates: Date[] = [new Date(), new Date()];
  aMoney = 0;
  cMoney = 0;
  aNumber = 0;
  cNumber = 0;
  aDistance = 0;
  cumDistance = 0;

  constructor(
    private userService: UserService,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.route.params.subscribe((params) => {
      this.id = Number(params['id']);
    });
  }
  selectDateRange() {
    console.log(this.rangeDates);
    let startDate = this.rangeDates[0];
    let endDate = this.rangeDates[1];
    startDate.setHours(startDate.getHours() + 1);
    endDate.setHours(endDate.getHours() + 1);
    this.userService
      .getUserReports(startDate, endDate, this.id)
      .subscribe((report) => {
        this.calculateAverageAndCumulative(report);
        this.basicData = {
          labels: report.labels,
          datasets: [
            {
              label: 'Money',
              backgroundColor: '#42A5F5',
              data: report.prices,
            },
            {
              label: 'Distance in km',
              backgroundColor: '#FFA726',
              data: report.distance,
            },
            {
              label: 'Number of rides',
              backgroundColor: '#FF0029',
              data: report.numberOfRides,
            },
          ],
        };
      });
  }

  calculateAverageAndCumulative(report: Report) {
    this.aMoney = this.average(report.prices);
    this.aDistance = this.average(report.distance);
    this.aNumber = this.average(report.numberOfRides);
    this.cMoney = report.prices.reduce((b, a) => b + a, 0);
    this.cumDistance = report.distance.reduce((b, a) => b + a, 0);
    this.cNumber = report.numberOfRides.reduce((b, a) => b + a, 0);
  }

  average(n: number[]) {
    return n.reduce((a, b) => a + b, 0) / n.length;
  }
}
