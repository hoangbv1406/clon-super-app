import { ComponentFixture, TestBed } from '@angular/core/testing';
import { StoreManagementDataAccess } from './store-management-data-access';

describe('StoreManagementDataAccess', () => {
  let component: StoreManagementDataAccess;
  let fixture: ComponentFixture<StoreManagementDataAccess>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StoreManagementDataAccess],
    }).compileComponents();

    fixture = TestBed.createComponent(StoreManagementDataAccess);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
