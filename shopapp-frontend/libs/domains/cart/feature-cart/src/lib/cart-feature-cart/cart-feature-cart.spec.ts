import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CartFeatureCart } from './cart-feature-cart';

describe('CartFeatureCart', () => {
  let component: CartFeatureCart;
  let fixture: ComponentFixture<CartFeatureCart>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CartFeatureCart],
    }).compileComponents();

    fixture = TestBed.createComponent(CartFeatureCart);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
