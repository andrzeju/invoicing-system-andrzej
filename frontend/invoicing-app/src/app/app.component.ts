import { Component } from '@angular/core';
import { Company } from './company'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'invoicing-app';

  companies: Company[] = [
    new Company("555-555-55-55", "Autosan S.A.", "Puławska 22, 22-222 Warszawa", 222.22, 111.11),
    new Company("444-444-44-44", "Agito S.A.", "Piłsudskiego 44, 22-222 Warszawa", 555.22, 123.11)
  ];
}
