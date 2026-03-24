import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CheckoutFeatureBooking } from './checkout-feature-booking';

describe('CheckoutFeatureBooking', () => {
  let component: CheckoutFeatureBooking;
  let fixture: ComponentFixture<CheckoutFeatureBooking>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CheckoutFeatureBooking],
    }).compileComponents();

    fixture = TestBed.createComponent(CheckoutFeatureBooking);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
