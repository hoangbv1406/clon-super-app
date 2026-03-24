import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SocialHubDataAccess } from './social-hub-data-access';

describe('SocialHubDataAccess', () => {
  let component: SocialHubDataAccess;
  let fixture: ComponentFixture<SocialHubDataAccess>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SocialHubDataAccess],
    }).compileComponents();

    fixture = TestBed.createComponent(SocialHubDataAccess);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
