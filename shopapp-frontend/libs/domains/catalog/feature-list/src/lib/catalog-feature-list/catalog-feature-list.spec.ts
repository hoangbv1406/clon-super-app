import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CatalogFeatureList } from './catalog-feature-list';

describe('CatalogFeatureList', () => {
  let component: CatalogFeatureList;
  let fixture: ComponentFixture<CatalogFeatureList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CatalogFeatureList],
    }).compileComponents();

    fixture = TestBed.createComponent(CatalogFeatureList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
