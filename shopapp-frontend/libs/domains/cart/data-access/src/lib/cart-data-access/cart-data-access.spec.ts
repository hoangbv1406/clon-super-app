import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CartDataAccess } from './cart-data-access';

describe('CartDataAccess', () => {
  let component: CartDataAccess;
  let fixture: ComponentFixture<CartDataAccess>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CartDataAccess],
    }).compileComponents();

    fixture = TestBed.createComponent(CartDataAccess);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
