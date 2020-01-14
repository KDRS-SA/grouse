import {animate, state, style, transition, trigger} from '@angular/animations';

export const Animations = {
  toggleSlide : trigger('toggleSlide', [
    state('login', style({
      width: '100%',
      opacity: '1',
      visibility: 'visible'
    })),
  state('register', style({
    width: '0%',
    opacity: '0',
    visibility: 'hidden'
  })),
  transition('login => register', [
    animate('0.3s ease-out')
  ]),
  transition('register => login', [
    animate('0.3s ease-out')
  ])
]),
};
