import {animate, keyframes, state, style, transition, trigger} from '@angular/animations';

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
  shake: trigger('shake', [
    state('end', style({
      transform: 'scale(1)'
    })),
    transition('* => end', animate('750ms', keyframes([
        style({transform: 'translate3d(-1px, 0, 0)', offset: 0.1}),
        style({transform: 'translate3d(2px, 0, 0)', offset: 0.2}),
        style({transform: 'translate3d(-4px, 0, 0)', offset: 0.3}),
        style({transform: 'translate3d(4px, 0, 0)', offset: 0.4}),
        style({transform: 'translate3d(-4px, 0, 0)', offset: 0.5}),
        style({transform: 'translate3d(4px, 0, 0)', offset: 0.6}),
        style({transform: 'translate3d(-4px, 0, 0)', offset: 0.7}),
        style({transform: 'translate3d(2px, 0, 0)', offset: 0.8}),
        style({transform: 'translate3d(-1px, 0, 0)', offset: 0.9}),
        ]))),
  ])
};
