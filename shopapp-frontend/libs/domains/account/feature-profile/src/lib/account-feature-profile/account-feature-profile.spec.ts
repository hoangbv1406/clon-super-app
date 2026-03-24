import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AccountFeatureProfile } from './account-feature-profile';

describe('AccountFeatureProfile', () => {
  let component: AccountFeatureProfile;
  let fixture: ComponentFixture<AccountFeatureProfile>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AccountFeatureProfile],
    }).compileComponents();

    fixture = TestBed.createComponent(AccountFeatureProfile);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
