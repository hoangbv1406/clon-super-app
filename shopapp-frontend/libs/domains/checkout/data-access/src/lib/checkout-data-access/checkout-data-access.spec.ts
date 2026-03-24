import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CheckoutDataAccess } from './checkout-data-access';

describe('CheckoutDataAccess', () => {
  let component: CheckoutDataAccess;
  let fixture: ComponentFixture<CheckoutDataAccess>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CheckoutDataAccess],
    }).compileComponents();

    fixture = TestBed.createComponent(CheckoutDataAccess);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
