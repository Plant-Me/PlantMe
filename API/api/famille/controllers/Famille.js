'use strict';

/**
 * Famille.js controller
 *
 * @description: A set of functions called "actions" for managing `Famille`.
 */

module.exports = {

  /**
   * Retrieve famille records.
   *
   * @return {Object|Array}
   */

  find: async (ctx) => {
    if (ctx.query._q) {
      return strapi.services.famille.search(ctx.query);
    } else {
      return strapi.services.famille.fetchAll(ctx.query);
    }
  },

  /**
   * Retrieve a famille record.
   *
   * @return {Object}
   */

  findOne: async (ctx) => {
    return strapi.services.famille.fetch(ctx.params);
  },

  /**
   * Count famille records.
   *
   * @return {Number}
   */

  count: async (ctx) => {
    return strapi.services.famille.count(ctx.query);
  },

  /**
   * Create a/an famille record.
   *
   * @return {Object}
   */

  create: async (ctx) => {
    return strapi.services.famille.add(ctx.request.body);
  },

  /**
   * Update a/an famille record.
   *
   * @return {Object}
   */

  update: async (ctx, next) => {
    return strapi.services.famille.edit(ctx.params, ctx.request.body) ;
  },

  /**
   * Destroy a/an famille record.
   *
   * @return {Object}
   */

  destroy: async (ctx, next) => {
    return strapi.services.famille.remove(ctx.params);
  },

  /**
   * Add relation to a/an famille record.
   *
   * @return {Object}
   */

  createRelation: async (ctx, next) => {
    return strapi.services.famille.addRelation(ctx.params, ctx.request.body);
  },

  /**
   * Update relation to a/an famille record.
   *
   * @return {Object}
   */

  updateRelation: async (ctx, next) => {
    return strapi.services.famille.editRelation(ctx.params, ctx.request.body);
  },

  /**
   * Destroy relation to a/an famille record.
   *
   * @return {Object}
   */

  destroyRelation: async (ctx, next) => {
    return strapi.services.famille.removeRelation(ctx.params, ctx.request.body);
  }
};
