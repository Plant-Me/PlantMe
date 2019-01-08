'use strict';

/**
 * Plante.js controller
 *
 * @description: A set of functions called "actions" for managing `Plante`.
 */

module.exports = {

  /**
   * Retrieve plante records.
   *
   * @return {Object|Array}
   */

  find: async (ctx) => {
    if (ctx.query._q) {
      return strapi.services.plante.search(ctx.query);
    } else {
      return strapi.services.plante.fetchAll(ctx.query);
    }
  },

  /**
   * Retrieve a plante record.
   *
   * @return {Object}
   */

  findOne: async (ctx) => {
    return strapi.services.plante.fetch(ctx.params);
  },

  /**
   * Count plante records.
   *
   * @return {Number}
   */

  count: async (ctx) => {
    return strapi.services.plante.count(ctx.query);
  },

  /**
   * Create a/an plante record.
   *
   * @return {Object}
   */

  create: async (ctx) => {
    return strapi.services.plante.add(ctx.request.body);
  },

  /**
   * Update a/an plante record.
   *
   * @return {Object}
   */

  update: async (ctx, next) => {
    return strapi.services.plante.edit(ctx.params, ctx.request.body) ;
  },

  /**
   * Destroy a/an plante record.
   *
   * @return {Object}
   */

  destroy: async (ctx, next) => {
    return strapi.services.plante.remove(ctx.params);
  },

  /**
   * Add relation to a/an plante record.
   *
   * @return {Object}
   */

  createRelation: async (ctx, next) => {
    return strapi.services.plante.addRelation(ctx.params, ctx.request.body);
  },

  /**
   * Update relation to a/an plante record.
   *
   * @return {Object}
   */

  updateRelation: async (ctx, next) => {
    return strapi.services.plante.editRelation(ctx.params, ctx.request.body);
  },

  /**
   * Destroy relation to a/an plante record.
   *
   * @return {Object}
   */

  destroyRelation: async (ctx, next) => {
    return strapi.services.plante.removeRelation(ctx.params, ctx.request.body);
  }
};
