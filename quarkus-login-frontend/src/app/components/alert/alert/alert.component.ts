import { Component, Input, OnInit } from '@angular/core';
import { NavigationStart, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Alert, AlertType } from 'src/app/models';
import { AlertService } from 'src/app/service/alert.service';

@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html',
})
export class AlertComponent implements OnInit {
  @Input() id = 'default-alert';
  @Input() fade = true;

  alerts: Alert[] = [];
  alertSubscription: Subscription;
  routeSubscription: Subscription;

  constructor(private router: Router, private alertService: AlertService) {}

  ngOnInit() {
    //Subscribe una nueva alerta de notificacion
    this.alertSubscription = this.alertService
      .onAlert(this.id)
      .subscribe((alert) => {
        //Limpia las alertas cuando estan vacias
        if (!alert.message) {
          this.alerts = this.alerts.filter((x) => x.keepAfterRouteChange);
          this.alerts.forEach((x) => delete x.keepAfterRouteChange);
          return;
        }

        this.alerts.push(alert);
        if (alert.autoClose) {
          setTimeout(() => this.removeAlert(alert), 3000);
        }
      });

    this.routeSubscription = this.router.events.subscribe((event) => {
      if (event instanceof NavigationStart) {
        this.alertService.clear(this.id);
      }
    });
  }

  ngOnDestroy() {
    // unsubscribe to avoid memory leaks
    this.alertSubscription.unsubscribe();
    this.routeSubscription.unsubscribe();
  }

  removeAlert(alert: Alert) {
    if (!this.alerts.includes(alert)) return;

    if (this.fade) {
      alert.fade = true;
      setTimeout(() => {
        this.alerts = this.alerts.filter((x) => x !== alert);
      }, 250);
    } else {
      this.alerts = this.alerts.filter((x) => x !== alert);
    }
  }

  cssClass(alert: Alert) {
    if (!alert) return '';

    const classes = ['alert', 'alert-dismissable', 'mt-4', 'container'];

    const alertTypeClass = {
      [AlertType.SUCCESS]: 'alert alert-success',
      [AlertType.ERROR]: 'alert alert-danger',
      [AlertType.INFO]: 'alert alert-info',
      [AlertType.WARNING]: 'alert alert-warning',
    };

    classes.push(alertTypeClass[alert.type]);

    if (alert.fade) {
      classes.push('fade');
    }

    return classes.join(' ');
  }
}
