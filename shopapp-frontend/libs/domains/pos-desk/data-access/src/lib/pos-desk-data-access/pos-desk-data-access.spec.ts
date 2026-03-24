import { ComponentFixture, TestBed } from '@angular/core/testing';
import { PosDeskDataAccess } from './pos-desk-data-access';

describe('PosDeskDataAccess', () => {
  let component: PosDeskDataAccess;
  let fixture: ComponentFixture<PosDeskDataAccess>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PosDeskDataAccess],
    }).compileComponents();

    fixture = TestBed.createComponent(PosDeskDataAccess);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
