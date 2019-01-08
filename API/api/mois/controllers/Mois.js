'use strict';

/**
 * Mois.js controller
 *
 * @description: A set of functions called "actions" for managing `Mois`.
 */

module.exports = {

  /**
   * Retrieve mois records.
   *
   * @return {Object|Array}
   */

  find: async (ctx) => {
    if (ctx.query._q) {
      return strapi.services.mois.search(ctx.query);
    } else {
      return strapi.services.mois.fetchAll(ctx.query);
    }
  },

  /**
   * Retrieve a mois record.
   *
   * @return {Object}
   */

  findOne: async (ctx) => {
    return strapi.services.mois.fetch(ctx.params);
  },

  /**
   * Count mois records.
   *
   * @return {Number}
   */

  count: async (ctx) => {
    return strapi.services.mois.count(ctx.query);
  },

  /**
   * Create a/an mois record.
   *
   * @return {Object}
   */

  create: async (ctx) => {
    return strapi.services.mois.add(ctx.request.body);
  },

  /**
   * Update a/an mois record.
   *
   * @return {Object}
   */

  update: async (ctx, next) => {
    return strapi.services.mois.edit(ctx.params, ctx.request.body) ;
  },

  /**
   * Destroy a/an mois record.
   *
   * @return {Object}
   */

  destroy: async (ctx, next) => {
    return strapi.services.mois.remove(ctx.params);
  },

  /**
   * Add relation to a/an mois record.
   *
   * @return {Object}
   */

  createRelation: async (ctx, next) => {
    return strapi.services.mois.addRelation(ctx.params, ctx.request.body);
  },

  /**
   * Update relation to a/an mois record.
   *
   * @return {Object}
   */

  updateRelation: async (ctx, next) => {
    return strapi.services.mois.editRelation(ctx.params, ctx.request.body);
  },

  /**
   * Destroy relation to a/an mois record.
   *
   * @return {Object}
   */

  destroyRelation: async (ctx, next) => {
    return strapi.services.mois.removeRelation(ctx.params, ctx.request.body);
  }
};
