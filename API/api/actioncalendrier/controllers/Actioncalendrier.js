'use strict';

/**
 * Actioncalendrier.js controller
 *
 * @description: A set of functions called "actions" for managing `Actioncalendrier`.
 */

module.exports = {

  /**
   * Retrieve actioncalendrier records.
   *
   * @return {Object|Array}
   */

  find: async (ctx) => {
    if (ctx.query._q) {
      return strapi.services.actioncalendrier.search(ctx.query);
    } else {
      return strapi.services.actioncalendrier.fetchAll(ctx.query);
    }
  },

  /**
   * Retrieve a actioncalendrier record.
   *
   * @return {Object}
   */

  findOne: async (ctx) => {
    return strapi.services.actioncalendrier.fetch(ctx.params);
  },

  /**
   * Count actioncalendrier records.
   *
   * @return {Number}
   */

  count: async (ctx) => {
    return strapi.services.actioncalendrier.count(ctx.query);
  },

  /**
   * Create a/an actioncalendrier record.
   *
   * @return {Object}
   */

  create: async (ctx) => {
    return strapi.services.actioncalendrier.add(ctx.request.body);
  },

  /**
   * Update a/an actioncalendrier record.
   *
   * @return {Object}
   */

  update: async (ctx, next) => {
    return strapi.services.actioncalendrier.edit(ctx.params, ctx.request.body) ;
  },

  /**
   * Destroy a/an actioncalendrier record.
   *
   * @return {Object}
   */

  destroy: async (ctx, next) => {
    return strapi.services.actioncalendrier.remove(ctx.params);
  },

  /**
   * Add relation to a/an actioncalendrier record.
   *
   * @return {Object}
   */

  createRelation: async (ctx, next) => {
    return strapi.services.actioncalendrier.addRelation(ctx.params, ctx.request.body);
  },

  /**
   * Update relation to a/an actioncalendrier record.
   *
   * @return {Object}
   */

  updateRelation: async (ctx, next) => {
    return strapi.services.actioncalendrier.editRelation(ctx.params, ctx.request.body);
  },

  /**
   * Destroy relation to a/an actioncalendrier record.
   *
   * @return {Object}
   */

  destroyRelation: async (ctx, next) => {
    return strapi.services.actioncalendrier.removeRelation(ctx.params, ctx.request.body);
  }
};
