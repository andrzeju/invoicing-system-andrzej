import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { of } from 'rxjs';
import { AppComponent } from './app.component';
import { Company } from './company';
import { CompanyService } from './company.service';

describe('AppComponent', () => {

  let fixture: ComponentFixture<AppComponent>;
  let component: AppComponent;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [
        AppComponent
      ],
      providers: [
        {provide: CompanyService, useClass: MockCompanyService}
      ],
      imports: [
        FormsModule
      ]
    }).compileComponents();


    fixture = TestBed.createComponent(AppComponent);
    component = fixture.componentInstance;
    
    component.ngOnInit()
    fixture.detectChanges();

  });

  it('should create the app', () => {
    component.ngOnInit()
    fixture.detectChanges();
    expect(component).toBeTruthy();
  });

  it(`should display a list of companies`, () => {
    expect(fixture.nativeElement.innerText).toContain("555-555-55-55")
    expect(fixture.nativeElement.innerText).toContain("666-666-66-66")

    expect(component.companies.length).toBe(2)
    expect(component.companies).toBe(MockCompanyService.companies)
  });

  it(`newly added company is added to the list`, () => {
    const taxIdInput: HTMLInputElement = fixture.nativeElement.querySelector("input[name=taxIdentificationNumber]")
    taxIdInput.value = "777-777-77-77"
    taxIdInput.dispatchEvent(new Event('input'));

    const nameInput: HTMLInputElement = fixture.nativeElement.querySelector("input[name=name]")
    nameInput.value = "Third Ltd."
    nameInput.dispatchEvent(new Event('input'));

    const addressInput: HTMLInputElement = fixture.nativeElement.querySelector("input[name=address]")
    addressInput.value = "ul. Third 3"
    addressInput.dispatchEvent(new Event('input'));

    const addInvoiceBtn: HTMLElement = fixture.nativeElement.querySelector("#addCompanyBtn");
    addInvoiceBtn.click();
    fixture.detectChanges();
    expect(fixture.nativeElement.innerText).toContain("777-777-77-77")
  });

  class MockCompanyService {
    static companies : Company [] = [
      new Company(1, '555-555-55-55', 'Google Inc.', 'ul. Abc', 111.11, 222.22),
      new Company(2, '666-666-66-66', 'Facebook Ltd.', 'ul. Def', 333.33, 444.44)
    ];

    getCompanies() {
      return of(MockCompanyService.companies);
    }

    addCompany(company : Company) {
      MockCompanyService.companies.push(company);
      return of();
    }
  }

});
