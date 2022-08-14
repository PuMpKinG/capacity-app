import { Converter, Prototype } from './common.types';

/**
 * Util provides some re-usable convenience methods for JavaScript/TypeScript.
 */
export class Util {
  /**
   * Create a deep copy of an object. The result has no type information
   * unless a callback converter is provided.
   */
  static copy<Type>(object: Type, callback?: Converter<Type>): Type {
    if (callback) {
      return callback(Util.copy(object));
    }
    return JSON.parse(JSON.stringify(object));
  }

  /** Create a typed instance out of an arbitrary object. */
  static convertTo<Type>(prototype: Prototype<Type>, object: any): Type {
    return Object.assign(new prototype(), object);
  }

  /** Create an array of typed instances out of an arbitrary array. */
  static convertArrayTo<Type>(prototype: Prototype<Type>, objects: any[]): Type[] {
    if (objects) {
      for (let i = 0; i < objects.length; i++) {
        objects[i] = Util.convertTo(prototype, objects[i]);
      }
    }
    return objects;
  }

  /**
   * Create an array of typed instances out of an arbitrary array
   * using a specific type converter.
   */
  static convertArray<Type>(callback: Converter<Type>, objects: any[]): Type[] {
    if (objects) {
      for (let i = 0; i < objects.length; i++) {
        objects[i] = callback(objects[i]);
      }
    }
    return objects;
  }

  static findBy = <Type>(objects: Type[], callback: (object: Type) => boolean): Type =>
    (objects && objects.find((o) => callback(o))) || undefined;

  static filterBy = <Type>(objects: Type[], callback: (object: Type) => boolean): Type[] =>
    (objects && objects.filter((o) => callback(o))) || [];

  static sortNaturalBy = <Type>(objects: Type[], callback: (object: Type) => any): Type[] =>
    (objects &&
      objects.sort((o1, o2) => {
        const obj1 = ((o1 && callback(o1) + '') || '').toUpperCase();
        const obj2 = ((o2 && callback(o2) + '') || '').toUpperCase();
        if (obj1 < obj2) return -1;
        if (obj1 > obj2) return 1;
        return 0;
      })) ||
    [];

  static sortNumericalBy = <Type>(objects: Type[], callback: (object: Type) => number): Type[] =>
    (objects && objects.sort((o1, o2) => (callback(o1) || 0) - (callback(o2) || 0))) || [];

  static sumBy = <Type>(objects: Type[], callback: (object: Type) => number): number =>
    (objects && objects.map((o) => callback(o) || 0).reduce(Util.sum, 0)) || 0;

  static joinBy = <Type>(objects: Type[], callback: (object: Type) => string): string =>
    (objects && objects.map((o) => callback(o) || '').join(', ')) || '';

  /** A sum method to be used in map-reduce scenarios. */
  private static sum = (a: number, b: number) => a + b;

  static fraction = <Type>(total: number, divisor: number, multiplicator: number): number =>
    (total != undefined &&
      divisor != undefined &&
      multiplicator != undefined &&
      divisor != 0 &&
      (total / divisor) * multiplicator) ||
    0;
}
