import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CatalogDataAccess } from './catalog-data-access';

describe('CatalogDataAccess', () => {
  let component: CatalogDataAccess;
  let fixture: ComponentFixture<CatalogDataAccess>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CatalogDataAccess],
    }).compileComponents();

    fixture = TestBed.createComponent(CatalogDataAccess);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
