import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SocialHubFeatureInteraction } from './social-hub-feature-interaction';

describe('SocialHubFeatureInteraction', () => {
  let component: SocialHubFeatureInteraction;
  let fixture: ComponentFixture<SocialHubFeatureInteraction>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SocialHubFeatureInteraction],
    }).compileComponents();

    fixture = TestBed.createComponent(SocialHubFeatureInteraction);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
