import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BorrowItemComponent } from './borrow-item.component';

describe('BorrowItemComponent', () => {
  let component: BorrowItemComponent;
  let fixture: ComponentFixture<BorrowItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BorrowItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BorrowItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
