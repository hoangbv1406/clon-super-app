import { ComponentFixture, TestBed } from '@angular/core/testing';
import { PosDeskFeatureCashier } from './pos-desk-feature-cashier';

describe('PosDeskFeatureCashier', () => {
  let component: PosDeskFeatureCashier;
  let fixture: ComponentFixture<PosDeskFeatureCashier>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PosDeskFeatureCashier],
    }).compileComponents();

    fixture = TestBed.createComponent(PosDeskFeatureCashier);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
