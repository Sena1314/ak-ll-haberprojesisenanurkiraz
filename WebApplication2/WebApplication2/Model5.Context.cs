﻿//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated from a template.
//
//     Manual changes to this file may cause unexpected behavior in your application.
//     Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace WebApplication2
{
    using System;
    using System.Data.Entity;
    using System.Data.Entity.Infrastructure;
    
    public partial class HaberDBEntities2 : DbContext
    {
        public HaberDBEntities2()
            : base("name=HaberDBEntities2")
        {
        }
    
        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            throw new UnintentionalCodeFirstException();
        }
    
        public virtual DbSet<Kategori> Kategori { get; set; }
        public virtual DbSet<Makale> Makale { get; set; }
        public virtual DbSet<Üye> Üye { get; set; }
        public virtual DbSet<Yazar> Yazar { get; set; }
    }
}
