import { ComponentFixture, TestBed } from '@angular/core/testing';
import { StoreManagementFeatureDashboard } from './store-management-feature-dashboard';

describe('StoreManagementFeatureDashboard', () => {
  let component: StoreManagementFeatureDashboard;
  let fixture: ComponentFixture<StoreManagementFeatureDashboard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StoreManagementFeatureDashboard],
    }).compileComponents();

    fixture = TestBed.createComponent(StoreManagementFeatureDashboard);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
