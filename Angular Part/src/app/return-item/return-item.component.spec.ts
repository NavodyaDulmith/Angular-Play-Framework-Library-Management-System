import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReturnItemComponent } from './return-item.component';

describe('ReturnItemComponent', () => {
  let component: ReturnItemComponent;
  let fixture: ComponentFixture<ReturnItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReturnItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReturnItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
