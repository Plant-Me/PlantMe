'use strict';

/**
 * Plantecalendrier.js controller
 *
 * @description: A set of functions called "actions" for managing `Plantecalendrier`.
 */

module.exports = {

  /**
   * Retrieve plantecalendrier records.
   *
   * @return {Object|Array}
   */

  find: async (ctx) => {
    if (ctx.query._q) {
      return strapi.services.plantecalendrier.search(ctx.query);
    } else {
      return strapi.services.plantecalendrier.fetchAll(ctx.query);
    }
  },

  /**
   * Retrieve a plantecalendrier record.
   *
   * @return {Object}
   */

  findOne: async (ctx) => {
    return strapi.services.plantecalendrier.fetch(ctx.params);
  },

  /**
   * Count plantecalendrier records.
   *
   * @return {Number}
   */

  count: async (ctx) => {
    return strapi.services.plantecalendrier.count(ctx.query);
  },

  /**
   * Create a/an plantecalendrier record.
   *
   * @return {Object}
   */

  create: async (ctx) => {
    return strapi.services.plantecalendrier.add(ctx.request.body);
  },

  /**
   * Update a/an plantecalendrier record.
   *
   * @return {Object}
   */

  update: async (ctx, next) => {
    return strapi.services.plantecalendrier.edit(ctx.params, ctx.request.body) ;
  },

  /**
   * Destroy a/an plantecalendrier record.
   *
   * @return {Object}
   */

  destroy: async (ctx, next) => {
    return strapi.services.plantecalendrier.remove(ctx.params);
  },

  /**
   * Add relation to a/an plantecalendrier record.
   *
   * @return {Object}
   */

  createRelation: async (ctx, next) => {
    return strapi.services.plantecalendrier.addRelation(ctx.params, ctx.request.body);
  },

  /**
   * Update relation to a/an plantecalendrier record.
   *
   * @return {Object}
   */

  updateRelation: async (ctx, next) => {
    return strapi.services.plantecalendrier.editRelation(ctx.params, ctx.request.body);
  },

  /**
   * Destroy relation to a/an plantecalendrier record.
   *
   * @return {Object}
   */

  destroyRelation: async (ctx, next) => {
    return strapi.services.plantecalendrier.removeRelation(ctx.params, ctx.request.body);
  }
};
