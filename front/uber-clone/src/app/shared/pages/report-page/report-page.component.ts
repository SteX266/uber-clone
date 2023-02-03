import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
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
    console.log(startDate);
    console.log(endDate);
    this.userService
      .getUserReports(startDate, endDate, this.id)
      .subscribe((report) => {
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

    this.applyLightTheme();
  }

  applyLightTheme() {
    this.horizontalOptions = {
      indexAxis: 'y',
      plugins: {
        legend: {
          labels: {
            color: '#495057',
          },
        },
      },
      scales: {
        x: {
          ticks: {
            color: '#495057',
          },
          grid: {
            color: '#ebedef',
          },
        },
        y: {
          ticks: {
            color: '#495057',
          },
          grid: {
            color: '#ebedef',
          },
        },
      },
    };
  }
}
